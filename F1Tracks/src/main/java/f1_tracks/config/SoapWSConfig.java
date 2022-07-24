package f1_tracks.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapWSConfig extends WsConfigurerAdapter {

    public static final String GRANDPRIX_NAMESPACE = "http://sri4f1tracks.jwegrzyn.sri.pja.edu/grandprix";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet,"/ws/*");
    }

    @Bean(name = "grandprix")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema grandprixSchema){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GrandPrixPort");
        wsdl11Definition.setLocationUri("/ws/grandprix");
        wsdl11Definition.setTargetNamespace(GRANDPRIX_NAMESPACE);
        wsdl11Definition.setSchema(grandprixSchema);
        return wsdl11Definition;
    }



    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("grandprix.xsd"));
    }


}
