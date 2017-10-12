package idv.jack;


import java.io.Serializable;
import java.sql.Timestamp;

public class WebmailVO implements Serializable {

        private Integer mailNo;
        private Integer tomemNo;
        private Integer getmemNo;
        private String mailContext;
        private Timestamp mailDate;
        private byte[] mailImage;

        public Integer getMailNo() {
            return mailNo;
        }

        public void setMailNo(Integer mailNo) {
            this.mailNo = mailNo;
        }

        public Integer getTomemNo() {
            return tomemNo;
        }

        public void setTomemNo(Integer tomemNo) {
            this.tomemNo = tomemNo;
        }

        public Integer getGetmemNo() {
            return getmemNo;
        }

        public void setGetmemNo(Integer getmemNo) {
            this.getmemNo = getmemNo;
        }

        public String getMailContext() {
            return mailContext;
        }

        public void setMailContext(String mailContext) {
            this.mailContext = mailContext;
        }

        public Timestamp getMailDate() {
            return mailDate;
        }

        public void setMailDate(Timestamp mailDate) {
            this.mailDate = mailDate;
        }

        public byte[] getMailImage() {
            return mailImage;
        }

        public void setMailImage(byte[] mailImage) {
            this.mailImage = mailImage;
        }

    }


