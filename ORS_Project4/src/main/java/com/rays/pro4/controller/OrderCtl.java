
package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("productName"))) {
			request.setAttribute("productName", PropertyReader.getValue("error.require", "productName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("productName"))) {
			request.setAttribute("productName", "only letter are allowed ");
			pass = false;
		} else if (DataValidator.isTooLong(request.getParameter("productName"), 20)) {
			request.setAttribute("productName", "  only 20 charector are allowed  ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("orderDate"))) {
			request.setAttribute("orderDate", PropertyReader.getValue("error.require", " orderDate"));
			pass = false;
		}

		else if (!DataValidator.isDate(request.getParameter("orderDate"))) {
			request.setAttribute("orderDate", PropertyReader.getValue("error.date", "orderDate  "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "quantity"));
			pass = false;
		}

		else if (!DataValidator.isLong(request.getParameter("quantity"))) {
			request.setAttribute("quantity", "Only numbers are allowed.");
			pass = false;
		}
		 else if (DataValidator.isTooLong(request.getParameter("quantity"), 15)) {
				request.setAttribute("quantity", "  only 15 digits are allowed  ");
				pass = false;
			}
		if (DataValidator.isNull(request.getParameter("customer"))) {
			request.setAttribute("customer", PropertyReader.getValue("error.require", "customer"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected void preload(HttpServletRequest request) {
		OrderModel model = new OrderModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "Prachi");
		map.put(2, "Dipanshi");
		map.put(3, "Pooja ");

		request.setAttribute("cst", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		OrderBean bean = new OrderBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setProductName(DataUtility.getString(request.getParameter("productName")));
		bean.setOrderDate(DataUtility.getDate(request.getParameter("orderDate")));
		bean.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		bean.setCustomer(DataUtility.getString(request.getParameter("customer")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		OrderModel model = new OrderModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("Order Edit Id >= " + id);

		if (id != 0 || op != null) {

			System.out.println("in id > 0  condition " + id);
			OrderBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>" + id + op);

		OrderModel model = new OrderModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			OrderBean bean = (OrderBean) populateBean(request);

			try {
				if (id > 0) {
					// System.out.println("hi i am in dopost update");

					model.update(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Order  is successfully Updated", request);
				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Order is successfully Added", request);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			OrderBean bean = (OrderBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ORDER_VIEW;
	}

}