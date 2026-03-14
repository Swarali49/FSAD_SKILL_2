package com.demo.main;

import org.hibernate.Session;

import com.demo.entity.Product;
import com.demo.util.HibernateUtil;

public class MainApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // INSERT PRODUCTS
        session.beginTransaction();

        Product p1 = new Product("Laptop","Gaming Laptop",80000,5);
        Product p2 = new Product("Mouse","Wireless Mouse",800,20);

        session.persist(p1);
        session.persist(p2);

        session.getTransaction().commit();

        System.out.println("Products inserted");


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