package com.alfred.Sailfish.app.Util;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MainDatabase?useUnicode=true&characterEncoding=utf8&useOldAliasMetadataBehavior=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "cxycxy11";
  
    private static DataSource ds = null;
    
    /** 
     * 初始化连接池代码块 
     */  
    static {  
    	try {
    		ComboPooledDataSource cpds = new ComboPooledDataSource();
    		System.out.println(cpds.toString());
			cpds.setDriverClass(JDBC_DRIVER);
			cpds.setJdbcUrl(JDBC_URL);
			cpds.setUser(USER);
			cpds.setPassword(PASSWORD);
			cpds.setInitialPoolSize(5);
			cpds.setMaxPoolSize(500);
			cpds.setAcquireIncrement(3);
			cpds.setAcquireRetryAttempts(60);
			cpds.setAcquireRetryDelay(1000);
			cpds.setAutoCommitOnClose(false);
			cpds.setCheckoutTimeout(3000);
			cpds.setIdleConnectionTestPeriod(120);
			cpds.setMaxIdleTime(600);
			cpds.setMaxStatements(0);
			cpds.setTestConnectionOnCheckin(false);
			cpds.setMaxStatements(8);
			cpds.setMaxStatementsPerConnection(5);
			cpds.setUnreturnedConnectionTimeout(3);
			cpds.setMaxConnectionAge(20);
			ds = cpds;
			System.out.println(ds.toString());
    	}catch (PropertyVetoException e) {
			e.printStackTrace();
		}
    }
    
    private static DataSource getDs() {
		return ds;
	}

	/** 
     * 获取数据库连接对象 
     *  
     * @return 数据连接对象 
     * @throws SQLException 
     */  
    static synchronized Connection getConnection() throws SQLException {
    	Connection connection = null;
    	try {
    		connection = ds.getConnection();
		}catch (Exception e) {
    		e.printStackTrace();
		}
		//final Connection conn = getDs().getConnection();

		assert connection != null;
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }

	static void closeConn(Connection conn){
		try {
			if(conn!=null && conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
