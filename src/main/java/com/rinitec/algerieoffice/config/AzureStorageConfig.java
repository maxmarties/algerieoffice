package com.rinitec.algerieoffice.config;


import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;

@Configuration
public class AzureStorageConfig {

	@Value("${azure.storage.connection-string}")
    private String connectionString;
	
	@Bean
	public CloudBlobClient cloudBlobClient() throws URISyntaxException, StorageException, InvalidKeyException {
		final CloudStorageAccount storageAccount = CloudStorageAccount.parse(connectionString);
	    return storageAccount.createCloudBlobClient();
	}
	
}
