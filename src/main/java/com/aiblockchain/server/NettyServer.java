/**
 * 
 */
package com.aiblockchain.server;

/**
 * @author Athi
 *
 */
public class NettyServer {

    /*public NettyServer(Integer port) {
        // Configure the server
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        // Register the REST endpoints
        ResourceConfig config = new ResourceConfig();
        config.register(HelloWorldResource.class)
        .register(UserResource.class)
        .register(AIBlockChainResource.class)
        .register(SapHana2Resource.class)
        .register(DbResource.class)
        .register(MoxyJsonFeature.class)
        .register(MoxyXmlFeature.class)
        //.register(JaxbContextResolver.class)  // No need to register this provider if no special configuration is required.
        .register(JettisonFeature.class)
        .register(JacksonFeature.class)
        .register(MultiPartFeature.class)
        .register(org.glassfish.jersey.filter.LoggingFilter.class);
        
        Channel server = NettyHttpContainerProvider.createServer(baseUri, config, false);

		PropertyConfigurator.configure(System
				.getProperty("log4j.configuration"));
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ServerSpringConfig.class);
		
		// For the destroy method to work.
		context.registerShutdownHook();

        System.out.println("Netty jersey server started with Uri: " + baseUri.toString());
    }*/

    /*public static void main(String[] args) {
        Integer port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = NettyConfig.PORT;
        }
        new NettyServer(port);
    }*/
}
