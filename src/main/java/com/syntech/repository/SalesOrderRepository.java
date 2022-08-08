/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syntech.repository;

import com.syntech.model.SalesOrder;
import com.syntech.model.SalesOrderDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author rasmi
 */
@Stateless
public class SalesOrderRepository extends AbstractRepository<SalesOrder> {

    @PersistenceContext(name = "psDS")
    private EntityManager em;

    public SalesOrderRepository() {
        super(SalesOrder.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalesOrderRepository filterByIdEagerLoad(Long saleOrderId) {
        Join<SalesOrder, SalesOrderDetail> salesItemJoin = (Join<SalesOrder, SalesOrderDetail>) 
                root.<SalesOrder, SalesOrderDetail>fetch("salesOrderDetailList", JoinType.LEFT);
        Predicate criteriaPredicates = criteriaBuilder. equal(salesItemJoin.get("salesOrder").get("id"), saleOrderId);
        this.addCriteria(criteriaPredicates);
        return this;
    }

    public SalesOrder eagerload(Long soid) {
        SalesOrder so = null;
        try {
//            Query query = em.createQuery("SELECT e FROM SalesOrder e "
//                    + "INNER JOIN FETCH e.salesOrderDetailList t WHERE e.id=:soId", SalesOrder.class);
//            query.setParameter("soId", soid);
//            so = (SalesOrder) query.getSingleResult();

            so = ((SalesOrderRepository) this.startQuery()).filterByIdEagerLoad(soid).getSingleResult();

        } catch (Exception e) {
            Logger.getLogger(SalesOrderRepository.class.getName())
                    .log(Level.SEVERE, " Error while eager loading:", e);
            so = null;
        }
        return so;
    }

    public List<SalesOrder> eagerLoadAll() {
        List<SalesOrder> salesOrderList = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT e FROM SalesOrder e "
                    + "INNER JOIN FETCH e.salesOrderDetailList t");
            salesOrderList = query.getResultList();

//            salesOrderList = ((SalesOrderRepository) this.startQuery()).filterByEagerLoadAll().getResultList();
        } catch (Exception e) {
            System.out.println("Sales data doesn't exists!!!");
        }
        return salesOrderList;
    }

    public SalesOrderRepository filterByDate(Date date) {
        Predicate criteriaPredicates = criteriaBuilder.equal(root.get("date"), date);
        this.addCriteria(criteriaPredicates);
        return this;
    }

    public List<SalesOrder> findByDate(Date date) {
        List<SalesOrder> salesOrderList = new ArrayList<>();
        try {
//            Query query = em.createQuery("select e from SalesOrder e where e.date =:date");
//            query.setParameter("date", date);
//            salesOrderList = query.getResultList();

            salesOrderList = ((SalesOrderRepository) this.startQuery()).filterByDate(date).getResultList();

        } catch (Exception e) {
            System.out.println("Record Display Failed!!!");
        }
        return salesOrderList;
    }

    public BigDecimal calculateTotalAmountBeforeDate(Date date) {
        BigDecimal amount = BigDecimal.ZERO;
        try {
            Query query = em.createQuery("SELECT sum(e.totalAmount) from SalesOrder e where e.date<:date", BigDecimal.class);
            query.setParameter("date", date);
            amount = (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            amount = BigDecimal.ZERO;
        }
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
