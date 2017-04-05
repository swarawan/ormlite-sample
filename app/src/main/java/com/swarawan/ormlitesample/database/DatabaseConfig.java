package com.swarawan.ormlitesample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.swarawan.ormlitesample.database.entity.Employee;

import java.sql.SQLException;

/**
 * Created by Rio Swarawan on 12/7/2015.
 */
public class DatabaseConfig extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "sample-db";
    private static final int DB_VERSION = 1;

    private Dao<Employee, Integer> employeeDao = null;

    public DatabaseConfig(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        init();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        dropUpdatedTable();
        init();
    }

    private void init() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Employee.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropUpdatedTable() {
        try {
            TableUtils.dropTable(connectionSource, Employee.class, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearTable() {
        try {
            TableUtils.clearTable(connectionSource, Employee.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> void clearTable(Class<T> dataClass) {
        try {
            TableUtils.clearTable(connectionSource, dataClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Employee, Integer> getEmployeeDao() throws SQLException {
        if (employeeDao == null) {
            employeeDao = getDao(Employee.class);
        }
        return employeeDao;
    }
}
