/**
 * 
 */
package com.aiblockchain.server.websocket;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Athi
 *
 */
public class AIBlockChainServerOutboundHandler extends ChannelHandlerAdapter{
	private static final Logger logger = Logger.getLogger(AIBlockChainServerOutboundHandler.class);

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		logger.info("handlerRemoved Client closed for ctx " + ctx.name());
		System.out.println("handlerRemoved Client closed for ctx " + ctx.name());
		super.handlerRemoved(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelInactive Client closed for ctx " + ctx.name());
		System.out.println("channelInactive Client closed for ctx " + ctx.name());
		//ctx.close();
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.info("exceptionCaught Client closed for ctx " + ctx.name());
		System.out.println("exceptionCaught Client closed for ctx " + ctx.name());
		cause.printStackTrace();
		//ctx.close();
		super.exceptionCaught(ctx, cause);
	}	
}
