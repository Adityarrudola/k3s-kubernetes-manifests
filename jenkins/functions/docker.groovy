def call(cfg) {

    withCredentials([azureServicePrincipal(
        credentialsId: 'azure-sp',
        subscriptionIdVariable: 'AZ_SUB',
        clientIdVariable: 'AZ_CLIENT',
        clientSecretVariable: 'AZ_SECRET',
        tenantIdVariable: 'AZ_TENANT'
    )]) {

        sh """
        az login --service-principal \
          -u $AZ_CLIENT \
          -p $AZ_SECRET \
          --tenant $AZ_TENANT

        az account set --subscription $AZ_SUB

        az acr login --name ${cfg.ACR_NAME}
        """

        sh """
        docker push ${cfg.FULL_IMAGE}
        docker push ${cfg.ACR_LOGIN_SERVER}/${cfg.IMAGE_NAME}:latest
        """
    }
}

return this