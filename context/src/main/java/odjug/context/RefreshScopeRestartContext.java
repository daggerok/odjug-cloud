package odjug.context;

import odjug.context.props.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class RefreshScopeRestartContext {

    /*
    refresh scope

    1. run config, discovery and context apps
    2. check props: http://context/
    3. update props.name, commit and push odjug-cloud-config/context.yml
    4. push refresh: curl -XPOST http://context/admin/refresh
    5. verify response: ["props.name"]
    6. verify context app logs:

    s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@38a305ff...
    ...
    c.c.c.ConfigServicePropertySourceLocator : Fetching config from server at: http://localhost:8888
    c.c.c.ConfigServicePropertySourceLocator : Located environment: name=context, profiles=[default], label=null, version=001da95c821460005a2495983a810917edfc39a3
    b.c.PropertySourceBootstrapConfiguration : Located property source: CompositePropertySource [name='configService', propertySources=[MapPropertySource [name='https://github.com/daggerok/odjug-cloud-config/context.yml'], MapPropertySource [name='https://github.com/daggerok/odjug-cloud-config/application.yml']]]
     */

    /*
    actuator use case: restarting context

    1. update config file
    2. restart application context: curl -XPOST http://context/admin/restart
    3. verify response: {"message":"Restarting"}
    4. verify application logs

    note 1: you can do more, for example change server.port.
    note 2: or even more - shutting down application, so take care about security
     */

    public static void main(String[] args) {
        SpringApplication.run(RefreshScopeRestartContext.class, args);
    }
}
