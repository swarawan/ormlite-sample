package com.swarawan.ormlitesample.database.controller;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.swarawan.ormlitesample.database.DatabaseConfig;
import com.swarawan.ormlitesample.database.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rioswarawan on 4/5/17.
 */

public class EmployeeController {

    private Context context;
    private DatabaseConfig database;

    public EmployeeController(Context context) {
        this.context = context;
        if (database == null) {
            this.database = new DatabaseConfig(context);
        }
    }

    /**
     * Helper for {@link #insertUpdateDatabase(Employee)}
     * Do check weather data inserted is already exist, by having `id` on it. So existing data will be updated
     * If not, entity is new data.
     *
     * @param entity data to be insert
     */
    public void insert(Employee entity) {
        Employee temp = getEmployeeById(entity.getId());
        if (temp == null)
            temp = new Employee();

        temp.setId(entity.getId());
        temp.setName(entity.getName());
        temp.setAddress(entity.getAddress());
        temp.setPhone(entity.getPhone());
        temp.setCreated(new Date());
        temp.setUpdated(new Date());

        insertUpdateDatabase(temp);
    }

    /**
     * Insert employee data into local daabase
     *
     * @param entities employee entity
     */
    private void insertUpdateDatabase(Employee entities) {
        try {
            database.getEmployeeDao().createOrUpdate(entities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear all data from this table
     */
    public void clear() {
        database.clearTable();
    }

    /**
     * Get all employee
     *
     * @return collection of stored employees
     */
    public List<Employee> getEmployees() {
        List<Employee> data = new ArrayList<>();
        try {
            Dao<Employee, Integer> dao = database.getEmployeeDao();
            QueryBuilder<Employee, Integer> query = dao.queryBuilder();

            data = query.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get specific employee by adding clause where id=`$id`
     *
     * @param id insert id for where clause
     * @return specific employee
     */
    public Employee getEmployeeById(String id) {
        List<Employee> data = new ArrayList<>();
        try {
            Dao<Employee, Integer> dao = database.getEmployeeDao();
            QueryBuilder<Employee, Integer> query = dao.queryBuilder();
            query.where().eq("id", id);

            data = query.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (data.size() > 0)
            return data.get(0);
        return null;
    }
}
