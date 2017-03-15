package transportproject.transportwebsite.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"transportproject.transportwebsite.configuration"})
public class HibernateConfiguration {

    private static final String DATASOURCE_PROPERTIES_PATH = "datasource.properties";
    private static final String HIBERNATE_PROPERTIES_PATH = "hibernate.properties";

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        // TODO узнать стоит ли заменить это все дело на что-то другое, чтобы на серваке не валилось
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("transportproject.transportwebsite.model");
        final Properties properties = getProperties(HIBERNATE_PROPERTIES_PATH);
        sessionFactoryBean.setHibernateProperties(properties);
        return sessionFactoryBean;
    }


    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        final Properties properties = getProperties(DATASOURCE_PROPERTIES_PATH);
        dataSource.setUrl((String) properties.get("jdbc.url"));
        dataSource.setDriverClassName((String) properties.getProperty("jdbc.driverClassName"));
        dataSource.setUsername((String) properties.getProperty("jdbc.username"));
        dataSource.setPassword((String) properties.getProperty("jdbc.password"));
        return dataSource;
    }

    private Properties getProperties(String source) {
        Properties properties = new Properties();
        try (final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(source)){
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}
