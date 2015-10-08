/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.iot.service.sdk;

import org.apache.commons.codec.binary.Base64;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class Message
{
    /**
    * [Required for two way requests] Used to correlate two-way communication.
    * Format: A case-sensitive string ( up to 128 char long) of ASCII 7-bit alphanumeric chars
    * + {'-', ':', '/', '\', '.', '+', '%', '_', '#', '*', '?', '!', '(', ')', ',', '=', '@', ';', '$', '''}.
    * Non-alphanumeric characters are from URN RFC.
    **/
    private String messageId;

    public String getMessageId()
    {
        return messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    /**
    * Destination of the message
    **/
    private String to;

    public String getTo()
    {
        return to;
    }

    public void setTo(String deviceId)
    {
        this.to = "/devices/" + deviceId + "/messages/devicebound";
    }

    /**
    * Expiry time in UTC Interpreted by hub on C2D messages. Ignored in other cases.
    **/
    private Date expiryTimeUtc;

    public Date getExpiryTimeUtc()
    {
        return expiryTimeUtc;
    }

    /**
    * Used by receiver to Abandon, Reject or Complete the message
    **/
    private String lockToken;

    public String getLockToken()
    {
        return lockToken;
    }

    /**
    * Used in message responses and feedback
    **/
    public String correlationId;

    public String getCorrelationId()
    {
        return correlationId;
    }

    public void setCorrelationId(String correlationId)
    {
        this.correlationId = correlationId;
    }

    /**
    * [Required in feedback messages] Used to specify the origin of messages generated by device hub.
    * Possible value: “{hub name}/”
    **/
    private String userId;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
    * [Optional] On C2D messages it is interpreted by hub to specify the expected feedback messages. Ignored in other cases.
    **/
    private DeliveryAcknowledgement ack;

    public DeliveryAcknowledgement getAck()
    {
        return DeliveryAcknowledgement.None;
    }

    public void setAck(DeliveryAcknowledgement ack) throws NotImplementedException
    {
        throw new NotImplementedException();
    }

    /**
    * [Optional] Used when batching on HTTP Default: false.
    **/
    private Boolean httpBatchSerializeAsString;

    /**
    * [Optional] Used when batching on HTTP Default: UTF-8.
    **/
    private StandardCharsets httpBatchEncoding;

    /**
    * [Stamped on servicebound messages by IoT Hub] The authenticated id used to send this message.
    **/
    private String connectionDeviceId;

    /**
    * [Stamped on servicebound messages by IoT Hub] The generationId of the authenticated device used to send this message.
    **/
    private String connectionDeviceGenerationId;

    /**
    * [Stamped on servicebound messages by IoT Hub] The authentication type used to send this message, format as in IoT Hub Specs
    **/
    private String connectionAuthenticationMethod;

    /**
    * [Required in feedback messages] Used in feedback messages generated by IoT Hub.
    * 0 = success 1 = message expired 2 = max delivery count exceeded 3 = message rejected
    **/
    private FeedbackStatusCodeEnum feedbackStatusCode;

    /**
    * [Required in feedback messages] Used in feedback messages generated by IoT Hub. "success", "Message expired", "Max delivery count exceeded", "Message rejected"
    **/
    private String feedbackDescription;

    /**
    * [Required in feedback messages] Used in feedback messages generated by IoT Hub.
    **/
    private String feedbackDeviceId;

    /**
    * [Required in feedback messages] Used in feedback messages generated by IoT Hub.
    **/
    private String feedbackDeviceGenerationId;

    /**
    * A bag of user-defined properties. Value can only be strings. These do not contain system properties.
    **/
    private Map<String,String> properties;

    /**
    * The message body
    **/
    private byte[] body;

    /**
    * Basic constructor
    **/
    public Message()
    {
    }

    /**
    * stream: a stream containing the body of the message
     * @param stream
    **/
    public Message(ByteArrayInputStream stream)
    {
        if (stream != null)
        {
            this.body = stream.toString().getBytes();
        }
    }

    /**
    * byteArray: a byte array containing the body of the message
     * @param byteArray
    **/
    public Message(byte[] byteArray)
    {
        this.body = byteArray;
    }

    /**
     * @param string - a string containing the body of the message.
     * Important: If a string is passed, the HttpBatch.SerializeAsString is set to true,
     * and the internal byte representation is serialized as UTF-8,
     * with HttpBatch.Encoding set to UTF-8.
     */
    public Message(String string) throws UnsupportedEncodingException
    {
        this.body = Base64.decodeBase64(string.getBytes("UTF-8"));
    }

    /**
    * The stream content of the body.
     * @return 
    **/
    public ByteArrayOutputStream getBodyStream()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.body.length);
        byteArrayOutputStream.write(this.body, 0, this.body.length);
        return byteArrayOutputStream;
    }

    /**
    * The byte content of the body.
     * @return 
    **/
    public byte[] getBytes()
    {
        return this.body;
    }
}