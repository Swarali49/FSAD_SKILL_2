package com.demo.main;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

import com.demo.entity.Product;
import com.demo.util.HibernateUtil;

public class MainApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // INSERT PRODUCTS
        session.beginTransaction();

        session.persist(new Product("Laptop","Gaming Laptop",80000,5));
        session.persist(new Product("Mouse","Wireless Mouse",800,20));
        session.persist(new Product("Keyboard","Mechanical Keyboard",2500,10));
        session.persist(new Product("Monitor","LED Monitor",12000,7));
        session.persist(new Product("Headphones","Bluetooth Headphones",3000,15));
        session.persist(new Product("Speaker","Portable Speaker",2000,12));
        session.persist(new Product("Webcam","HD Webcam",1500,6));

        session.getTransaction().commit();

        System.out.println("Products inserted");
        
        Query<Product> q1 = session.createQuery("FROM Product ORDER BY price ASC", Product.class);
        List<Product> list1 = q1.list();

        for(Product p : list1){
            System.out.println(p.getName()+" "+p.getPrice());
        }
        
        Query<Product> q2 = session.createQuery("FROM Product ORDER BY price DESC", Product.class);
        List<Product> list2 = q2.list();

        for(Product p : list2){
            System.out.println(p.getName()+" "+p.getPrice());
        }
        
        Query<Product> q3 = session.createQuery("FROM Product ORDER BY quantity DESC", Product.class);
        List<Product> list3 = q3.list();

        for(Product p : list3){
            System.out.println(p.getName()+" Quantity:"+p.getQuantity());
        }
        
        Query<Product> q4 = session.createQuery("FROM Product", Product.class);
        q4.setFirstResult(0);
        q4.setMaxResults(3);

        List<Product> page1 = q4.list();

        for(Product p : page1){
            System.out.println(p.getName());
        }
        
        Query<Product> q5 = session.createQuery("FROM Product", Product.class);
        q5.setFirstResult(3);
        q5.setMaxResults(3);

        List<Product> page2 = q5.list();

        for(Product p : page2){
            System.out.println(p.getName());
        }
        Query<Long> q6 = session.createQuery("SELECT COUNT(*) FROM Product", Long.class);
        System.out.println("Total products: "+q6.uniqueResult());
        
        Query<Long> q7 = session.createQuery("SELECT COUNT(*) FROM Product WHERE quantity > 0", Long.class);
        System.out.println("Available products: "+q7.uniqueResult());
        
        Query<Object[]> q8 = session.createQuery("SELECT MIN(price), MAX(price) FROM Product", Object[].class);
        Object[] result = q8.uniqueResult();

        System.out.println("Min price: "+result[0]);
        System.out.println("Max price: "+result[1]);
        
        Query<Object[]> q9 = session.createQuery(
        		"SELECT description, COUNT(*) FROM Product GROUP BY description",
        		Object[].class);

        		List<Object[]> list = q9.list();

        		for(Object[] row : list){
        		    System.out.println(row[0]+" -> "+row[1]);
        		}
        		
        		Query<Product> q10 = session.createQuery(
        				"FROM Product WHERE price BETWEEN 1000 AND 10000",
        				Product.class);

        				List<Product> list10 = q10.list();

        				for(Product p : list10){
        				    System.out.println(p.getName()+" "+p.getPrice());
        				}
        				Query<Product> q11 = session.createQuery(
        						"FROM Product WHERE name LIKE 'M%'", 
        						Product.class);

        						List<Product> list11 = q11.list();

        						for(Product p : list11){
        						    System.out.println(p.getName() + " " + p.getPrice());
        						}
        						Query<Product> q12 = session.createQuery(
        								"FROM Product WHERE name LIKE '%r'", 
        								Product.class);

        								List<Product> list12 = q12.list();

        								for(Product p : list12){
        								    System.out.println(p.getName() + " " + p.getPrice());
        								}
        				Query<Product> q13 = session.createQuery(
        						"FROM Product WHERE name LIKE '%top%'", 
        						Product.class);

        						List<Product> list13 = q13.list();

        						for(Product p : list13){
        						    System.out.println(p.getName() + " " + p.getPrice());
        						}
        				Query<Product> q14 = session.createQuery(
        						"FROM Product WHERE length(name)=5",
        						Product.class);
        				List<Product> list14 = q14.list();

        				for(Product p : list14){
        				    System.out.println(p.getName());
        				}


        // READ PRODUCT
        session.beginTransaction();

        Product p = session.get(Product.class,1);

        if(p != null){
            System.out.println("Product Name: "+p.getName());
            System.out.println("Price: "+p.getPrice());
        }

        session.getTransaction().commit();


        // UPDATE PRODUCT
        session.beginTransaction();

        Product updateProduct = session.get(Product.class,1);

        if(updateProduct != null){
            updateProduct.setPrice(75000);
            session.merge(updateProduct);
            System.out.println("Product updated");
        }

        session.getTransaction().commit();


        // DELETE PRODUCT
        session.beginTransaction();

        Product deleteProduct = session.get(Product.class,2);

        if(deleteProduct != null){
        	session.remove(deleteProduct);
            System.out.println("Product deleted");
        } else {
            System.out.println("Product not found for deletion");
        }

        session.getTransaction().commit();
        
        

        session.close();
    }
}