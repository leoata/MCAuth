﻿using System;

namespace Test
{
    public class AuthAttempt
    {
        public string ip { get; set; }
        public string auth { get; set; }
        public string uuid { get; set; }

        public AuthAttempt(string ip, string auth, string uuid)
        {
            this.ip = ip;
            this.auth = auth;
            this.uuid = uuid;
        }
    }
}