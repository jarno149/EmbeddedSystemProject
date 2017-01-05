/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.utils;

import jdk.nashorn.internal.parser.JSONParser;

/**
 *
 * @author Jarno
 */
public class Settings
{
    private MqttSettings mqttSettings;
    private DatabaseSettings databaseSettings;

    public MqttSettings getMqttSettings()
    {
        return mqttSettings;
    }

    public void setMqttSettings(MqttSettings mqttSettings)
    {
        this.mqttSettings = mqttSettings;
    }

    public DatabaseSettings getDatabaseSettings()
    {
        return databaseSettings;
    }

    public void setDatabaseSettings(DatabaseSettings databaseSettings)
    {
        this.databaseSettings = databaseSettings;
    }
    
    public static class MqttSettings
    {
        private String url;
        private String userName;
        private String passWord;
        private int port;
        private String topic;

        public MqttSettings(String url, String userName, String passWord, int port, String topic)
        {
            this.url = url;
            this.userName = userName;
            this.passWord = passWord;
            this.port = port;
            this.topic = topic;
        }

        public MqttSettings()
        {
        }

        public String getUrl()
        {
            if(url.startsWith("tcp://"))
                return url + ":" + String.valueOf(port);
            else return "tcp://" + url + ":" + String.valueOf(port);
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
        
        public String getTopic()
        {
            return topic;
        }
        
        public void setTopic(String topic)
        {
            this.topic = topic;
        }

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public String getPassWord()
        {
            return passWord;
        }

        public void setPassWord(String passWord)
        {
            this.passWord = passWord;
        }

        public int getPort()
        {
            return port;
        }

        public void setPort(int port)
        {
            this.port = port;
        }
        
        
    }
    
    public static class DatabaseSettings
    {
        private String url;
        private int port;
        private String userName;
        private String passWord;

        public DatabaseSettings(String url, int port, String userName, String passWord)
        {
            this.url = url;
            this.port = port;
            this.userName = userName;
            this.passWord = passWord;
        }

        public DatabaseSettings()
        {
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public int getPort()
        {
            return port;
        }

        public void setPort(int port)
        {
            this.port = port;
        }

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public String getPassWord()
        {
            return passWord;
        }

        public void setPassWord(String passWord)
        {
            this.passWord = passWord;
        }
        
        
    }
}
