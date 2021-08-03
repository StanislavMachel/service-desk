package com.example.servicedesk.core.model;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.tuple.ValueGenerator;

import java.math.BigInteger;

public class TicketSerialNumberGenerator implements ValueGenerator<String> {
  @Override
  public String generateValue(Session session, Object owner) {
    NativeQuery nextvalQuery =
        session.createSQLQuery("select TICKET_NUMBER_SEQ.nextval from dual;");
    BigInteger number = (BigInteger) nextvalQuery.setFlushMode(FlushMode.COMMIT).uniqueResult();
    return String.format("%016d", number);
  }
}
