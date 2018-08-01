import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskTest {

    private  ApplicationContext ctx;

    @Before
    public void init(){
        String[] spring = {"applicationContext.xml", "applicationContext-shiro.xml", "applicationContext-task.xml"};
        ctx = new ClassPathXmlApplicationContext(spring);
    }

    @Test
    /**
     * 生成沙箱的api密钥
     */
    public void sendSms() {
//        SmsSendTask task = ctx.getBean(SmsSendTask.class);
//        task.sendBeforeMeeting();
    }
}
