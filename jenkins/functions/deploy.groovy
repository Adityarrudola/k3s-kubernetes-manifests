def call(cfg) {

    sh """
    cd ${cfg.BUILD_DIR}

    sed -i "s|image: .*|image: ${cfg.FULL_IMAGE}|g" k8s/deployment.yaml
    """

    sh """
    export KUBECONFIG=${cfg.KUBECONFIG}

    kubectl apply -f ${cfg.BUILD_DIR}/k8s/
    """

    sh """
    export KUBECONFIG=${cfg.KUBECONFIG}

    kubectl rollout status deployment/${cfg.IMAGE_NAME} --timeout=120s
    kubectl get pods -o wide
    """
}

return this