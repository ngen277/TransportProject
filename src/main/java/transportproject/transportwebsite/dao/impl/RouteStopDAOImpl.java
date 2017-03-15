package transportproject.transportwebsite.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import transportproject.transportwebsite.dao.RouteStopDAO;
import transportproject.transportwebsite.model.RouteStop;
import transportproject.transportwebsite.model.Stop;
import transportproject.transportwebsite.model.transport.TransportType;

import javax.transaction.Transactional;
import java.util.List;

@Repository("routeStopDAO")
@Transactional
public class RouteStopDAOImpl implements RouteStopDAO {

    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private Criteria createEntityCriteria() {
        return getSession().createCriteria(RouteStop.class);
    }

    @Autowired
    public RouteStopDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<RouteStop> getRouteStopsByTransportTypeAndRouteNumber(TransportType transportType, Integer routeNumber) {
        final Criteria entityCriteria = createEntityCriteria();
        entityCriteria.createAlias("route", "r")
                .createAlias("r.transport", "t")
                .add(Restrictions.eq("t.routeNumber", routeNumber));
//        entityCriteria.add(Restrictions.eq("route.transport.type", transportType));
//        entityCriteria.add(Restrictions.eq("route.transport.routeNumber", routeNumber));
        return entityCriteria.list();
    }

    @Override
    public RouteStop getRouteStopByRouteIdAndStopId(Integer routeId, Integer stopId) {
        final Criteria entityCriteria = createEntityCriteria();
        entityCriteria.add(Restrictions.eq("route.id", routeId))
                .add(Restrictions.eq("stop.id", routeId));
        return (RouteStop) entityCriteria.uniqueResult();
    }
}
