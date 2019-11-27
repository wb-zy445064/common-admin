package com.xieke.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xieke.admin.model.Order;
import com.xieke.admin.mapper.OrderMapper;
import com.xieke.admin.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangyang
 * @date 2019/09/07
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 添加
     *
     * @param order
     * @return
     */
    public boolean insert(Order order) {
        return orderMapper.insert(order) > 0;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public boolean delete(Integer id) {
        return orderMapper.deleteById(id) > 0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public Order get(Integer id) {
        return orderMapper.selectById(id);
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    public List<Order> findAll() {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        return orderMapper.selectList(wrapper);
    }

    public Boolean updatePaidAmount(Integer orderId, BigDecimal paidAmount) {
        if (orderId == null) {
            return false;
        }
        if (paidAmount == null) {
            return false;
        }
        Order order = this.get(orderId);
        order.setPaidAmount(paidAmount);
        return orderMapper.updateById(order) > 0;
    }

    public IPage<Order> findPage(Integer pageIndex, Integer pageSize, String studentName, String phoneOne) {
        Page<Order> objectPage = new Page<>(pageIndex, pageSize);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Order> wrapperLamdba = wrapper.lambda();
        if (!StringUtils.isEmpty(studentName)){
            wrapperLamdba.like(Order::getStudentName,studentName);
        }
        if (!StringUtils.isEmpty(phoneOne)){
            wrapperLamdba.eq(Order::getPhoneOne,phoneOne);
        }
        return orderMapper.selectPage(objectPage, wrapperLamdba);
    }


}