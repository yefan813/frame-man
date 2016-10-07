package com.frame.chat.api;

import com.frame.chat.comm.body.AudioMessageBody;
import com.frame.chat.comm.body.CommandMessageBody;
import com.frame.chat.comm.body.ImgMessageBody;
import com.frame.chat.comm.body.MessageBody;
import com.frame.chat.comm.body.TextMessageBody;
import com.frame.chat.comm.body.VideoMessageBody;

/**
 * This interface is created for RestAPI of Sending Message, it should be
 * synchronized with the API list.
 * 
 * @author Eric23 2016-01-05
 * @see http://docs.easemob.com/doku.php?id=start:100serverintegration:
 *      50messages
 */
public interface SendMessageAPI {
	/**
	 * 发送消息 <br>
	 * POST
	 * 
	 * @param payload
	 *            消息体
	 * @return
	 * @see MessageBody
	 * @see TextMessageBody
	 * @see ImgMessageBody
	 * @see AudioMessageBody
	 * @see VideoMessageBody
	 * @see CommandMessageBody
	 */
	Object sendMessage(Object payload);
}
