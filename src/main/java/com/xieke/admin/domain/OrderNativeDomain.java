package com.xieke.admin.domain;

import com.xieke.admin.bo.OrderBo;
import com.xieke.admin.bo.PayRecordBo;
import com.xieke.admin.model.Order;
import com.xieke.admin.page.HtPage;
import com.xieke.admin.service.OrderService;
import com.xieke.admin.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangyang
 * @date 2019/09/07
 */
@Service("orderDomain")
public class OrderNativeDomain implements OrderDomain {

    @Resource
    private OrderService orderService;

    @Resource
    private PayRecordDomain payRecordDomain;

    @Override
    public Integer insert(OrderBo orderBo) {
        Order order = BeanUtil.convert(orderBo, Order.class);
        orderService.insert(order);
        return order.getID();
    }

    @Override
    public boolean delete(Integer id) {
        return orderService.delete(id);
    }

    @Override
    public OrderBo get(Integer id) {
        return BeanUtil.convert(orderService.get(id), OrderBo.class);
    }

    @Override
    public List<OrderBo> findAll() {
        return BeanUtil.convertList(orderService.findAll(), OrderBo.class);
    }

    @Override
    public Boolean updatePaidAmountAfterPay(Integer orderId) {
        if (orderId == null) {
            return false;
        }
        OrderBo orderBo = this.get(orderId);
        if (orderBo == null) {
            return false;
        }
        BigDecimal resultPaidAmount = BigDecimal.ZERO;
        List<PayRecordBo> payRecordBoList = payRecordDomain.findByOrderId(orderId);
        if (!CollectionUtils.isEmpty(payRecordBoList)){
            for (PayRecordBo payRecordBo : payRecordBoList) {
                resultPaidAmount.add(payRecordBo.getPayAmount());
            }
        }

        return orderService.updatePaidAmount(orderId, resultPaidAmount);
    }

    @Override
    public HtPage<OrderBo> findPage(Integer pageIndex, Integer pageSize, String studentName, String phoneOne) {
        HtPage<Order> htPage = new HtPage<>(orderService.findPage(pageIndex, pageSize, studentName,phoneOne));
        return BeanUtil.convertPage(htPage, OrderBo.class);
    }

}