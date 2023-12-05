#!/bin/sh

CA_CNF_FILE="ca.cnf"
CA_KEY_FILE="ca.key"
CA_CRT_FILE="ca.crt"
CA_PEM_FILE="ca.pem"
IA_CNF_FILE="ia.cnf"
IA_KEY_FILE="ia.key"
IA_CSR_FILE="ia.csr"
IA_CRT_FILE="ia.crt"
MONGO_CNF_FILE="mongo.cnf"
MONGO_KEY_FILE="mongo.key"
MONGO_CSR_FILE="mongo.csr"
MONGO_CRT_FILE="mongo.crt"
MONGO_PEM_FILE="mongo.pem"

echo "Generating CA private key ..."
openssl genrsa -out ${CA_KEY_FILE} 4096
echo
sleep 1

echo "Generating CA root certificate ..."
openssl req -batch -new -x509 -days 1826 -key ${CA_KEY_FILE} -out ${CA_CRT_FILE} -config ${CA_CNF_FILE}
echo
sleep 1

echo "Generating IA private key ..."
openssl genrsa -out ${IA_KEY_FILE} 4096
echo
sleep 1

echo "Generating IA CSR ..."
openssl req -batch -new -key ${IA_KEY_FILE} -out ${IA_CSR_FILE} -config ${IA_CNF_FILE}
echo
sleep 1

echo "Generating IA certificate ..."
openssl x509 -sha256 -req -days 730 -in ${IA_CSR_FILE} -CA ${CA_CRT_FILE} -CAkey ${CA_KEY_FILE} -set_serial 01 -out ${IA_CRT_FILE} -extfile ${CA_CNF_FILE} -extensions v3_ca
echo
sleep 1

cat ${CA_CRT_FILE} ${IA_CRT_FILE} > ${CA_PEM_FILE}

echo "Generating MongoDB server private key and CSR ..."
openssl req -batch -config ${MONGO_CNF_FILE} -newkey rsa:4096 -nodes -keyout ${MONGO_KEY_FILE} -out ${MONGO_CSR_FILE}
echo
sleep 1

echo "Generating MongoDB server CA signed certificate ..."
openssl x509 -sha256 -req -days 365 -in ${MONGO_CSR_FILE} -CA ${IA_CRT_FILE} -CAkey ${IA_KEY_FILE} -CAcreateserial -out ${MONGO_CRT_FILE} -extfile ${MONGO_CNF_FILE} -extensions v3_req
echo
sleep 1

cat ${MONGO_CRT_FILE} ${MONGO_KEY_FILE} > ${MONGO_PEM_FILE}

echo "Generating keyfile"
openssl rand -base64 756 > ./keyfile
chmod 400 ./keyfile

echo "Cleaning crt, key, csr files"
rm *.crt
rm *.key
rm *.csr
rm *.srl
