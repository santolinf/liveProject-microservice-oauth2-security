keytool -genkey -noprompt -alias authorization-server -keyalg RSA -validity 365 -keystore authorization-server.jks -storetype JKS \
  -dname "C=GB, ST=, L=, O=, OU=, CN=authorization-server" \
  -storepass changeit

# to get the public key
keytool -list -rfc --keystore authorization-server.jks | openssl x509 -inform pem -pubkey
