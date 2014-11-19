package com;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class StackOverflowTest
{
    @Test
    public void testGetUserById() throws Exception {
        StackOverflow userDAO = new StackOverflow();
        Connection mockConnection = Mockito.mock(Connection.class);
        Properties mockProperties =  Mockito.mock(Properties.class);
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        DBConnection mockDbConnection = Mockito.mock(DBConnection.class);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        QueryReader mockQueryReader = Mockito.mock(QueryReader.class);


        PowerMockito.whenNew(DBConnection.class).withNoArguments()
                .thenReturn(mockDbConnection);
        PowerMockito.whenNew(QueryReader.class).withNoArguments()
                .thenReturn(mockQueryReader);


        String query = "select * from User where AssociateID=?;";
        Mockito.when(mockDbConnection.openConnection(mockProperties, mockInputStream)).thenReturn(mockConnection);
        Mockito.when(mockQueryReader.getQuery("sqlScript_selectUser.sql")).thenReturn("query");
        Mockito.when(mockConnection.prepareStatement("query")).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);

        Mockito.when(mockResultSet.getString(1)).thenReturn("message");
        User u=userDAO.getUserById("AB1234");
        assertEquals("EX112233", u.getAssociateId());
    }
}
