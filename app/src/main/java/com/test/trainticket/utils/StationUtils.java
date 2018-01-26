package com.test.trainticket.utils;

import android.database.sqlite.SQLiteDatabase;

import com.test.trainticket.app.TrainTicketApplication;
import com.test.trainticket.model.StationDao;
import com.test.trainticket.model.db.dao.DaoMaster;
import com.test.trainticket.model.db.dao.DaoSession;
import com.test.trainticket.model.db.dao.StationDaoDao;

/**
 * Created by Administrator on 2018/1/25.
 */

public class StationUtils {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public StationUtils() {
        if (mHelper == null || mDaoMaster == null || mDaoSession == null) {
            setDatabase();
        }
    }


    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(TrainTicketApplication.getInstance(), "traindb", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            setDatabase();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    public long insert(StationDao stationDao) {
        StationDaoDao stationDaoDao = getDaoSession().getStationDaoDao();
        return stationDaoDao.insert(stationDao);//添加一个
    }


    public void delete(Long id) {
        StationDaoDao stationDaoDao = getDaoSession().getStationDaoDao();
        stationDaoDao.deleteByKey(id);
    }

    public void update(StationDao stationDao) {
        StationDaoDao stationDaoDao = getDaoSession().getStationDaoDao();
        stationDaoDao.update(stationDao);
    }


    public StationDao query(String name) {
        StationDaoDao stationDaoDao = getDaoSession().getStationDaoDao();
        StationDao stationDao = stationDaoDao.queryBuilder().where(StationDaoDao.Properties.Name.eq(name)).unique();
        return stationDao;
    }


}
