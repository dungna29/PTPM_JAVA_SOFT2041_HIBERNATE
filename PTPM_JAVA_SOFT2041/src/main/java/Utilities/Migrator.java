/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;


import DomainModels.Category;
import DomainModels.Product;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Phong
 */
public class Migrator {

  //Tạo DB trong SQL SERVER = SOFT2041_PTPM
  //Sau đó tiến hành chạy đển zen bảng
    public static void main(String[] args) {
        // Tạo đối tượng ServiceRegistry từ hibernate.cfg.xml
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("META-INF/hibernate.cfg.xml")
                .applySetting("hibernate.hbm2ddl.auto", "create")
                .build();

        // Tạo nguồn siêu dữ liệu (metadata) từ ServiceRegistry
        Metadata metadata = new MetadataSources(serviceRegistry)
                .getMetadataBuilder().build();

        SessionFactory factory = metadata.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction trans = session.beginTransaction();
        
        Category c1 = new Category();
        c1.setName("Tiêu dùng");
        c1.setId((Long) session.save(c1));
        
        Product p1 = new Product();
        p1.setName("Bàn chải");
        p1.setPrice(BigDecimal.valueOf(10000));
        p1.setCategory(c1);
        session.save(p1);
        
        Category c2 = new Category();
        c2.setName("Thực phẩm");
        c2.setId((Long) session.save(c2));
        
        Category c3 = new Category();
        c3.setName("Khác");
        c3.setId((Long) session.save(c3));
        
        trans.commit();
    }
}
