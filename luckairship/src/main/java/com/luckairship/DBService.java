package com.luckairship;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.luckairship.pojo.LuckAirShipPo;





public class DBService {
	private static SessionFactory sessionFactory = null;
	private static int hibernateBatchSize;

	public static Session getHibernateSession() {
		try {
			if (sessionFactory == null) {
				AnnotationConfiguration configuration = new AnnotationConfiguration().configure();
				hibernateBatchSize = 500;
				sessionFactory = configuration.buildSessionFactory();
			}
			return sessionFactory.getCurrentSession();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public static void saveLuckeyAirShipData(List<LuckAirShipPo> luckAirShip) throws HibernateException {
		Session session = null;
		Transaction transaction = null;

		Iterator<LuckAirShipPo> it = luckAirShip.iterator();

		while (it.hasNext()) {
			try {
				session = getHibernateSession();
				transaction = session.beginTransaction();

				int numRecordsProcessed = 0;

				while (numRecordsProcessed < hibernateBatchSize && it.hasNext()) {
					LuckAirShipPo wsBean = it.next();
					session.save(wsBean);
					// log.info("Webservices record " + wsBean + " saved.");
					numRecordsProcessed++;
				}
				session.flush();
			} catch (HibernateException e) {
				throw new HibernateException("Cannot save member", e);
			} finally {
				transaction.commit();
			}
		}

	}

	public static void main(String[] args) {

	}

}