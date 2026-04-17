def call(cfg) {

    sh """
    mkdir -p ${cfg.BUILD_DIR}
    cp -r * ${cfg.BUILD_DIR}/
    """

    sh """
    cd ${cfg.BUILD_DIR}/app

    docker build \
      -t ${cfg.FULL_IMAGE} \
      -t ${cfg.ACR_LOGIN_SERVER}/${cfg.IMAGE_NAME}:latest \
      .
    """
}