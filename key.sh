gpg --armor --export-secret-keys smdawe@yahoo.com \
    | awk 'NR == 1 { print "GPG_SIGNING_KEY=" } 1' ORS='\\n' \
    >> key.properties