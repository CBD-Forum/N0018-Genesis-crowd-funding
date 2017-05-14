package com.fbd.core.base;

import javax.servlet.http.HttpServletRequest;

import com.fbd.core.helper.FormBeanFill;


/**
 * 所有输入表单的基础父类,用作输入表单的语义对象。 
 * BaseForm用于声明Action的输入输出数据，有下列用途：
 * 1.与页面输入参数对应，实现输入数据的解析。
 * 2.与页面显示数据对应，用于页面数据显示。 
 * 3.基于表单的输入校验处理。
 * 
 * BaseForm是Action与视图层交换数据的载体，它与BaseDTO之间进行转换： 
 * 1.在页面请求时，由页面参数解析到Action的BaseForm参数中。
 * 2.Action调用Facade业务逻辑时，需要将BaseForm转换为BaseDTO对象。
 * 3.Action调用业务逻辑后，需要将BaseDTO转换为BaseForm，用于页面数据显示。
 * 
 * @author brz
 * @version 1.00
 * @date 2009-3-30
 * @see BaseDTO
 */
public class BaseForm {
	/**
	 * 
	 * 设置BaseDTO对象。
	 * 
	 */
//	public void setDTO(BaseDTO dto)	{
//		try {
//			BeanUtils.copyProperties(this, dto);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/**
//	 * 转换为BaseDTO对象。
//	 */
//	public BaseDTO toDTO(BaseDTO dto){
//		try {
//			BeanUtils.copyProperties(dto, this);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return dto;
//	}
	
	//////////////使用自己封装方法
    /**
     *  form 2 dto
     */
//	public void form2Dto(BaseDTO dto){
//		BeanCopy.copyBean(this, dto);
//	}
//	/**
//	 * dto 2 form
//	 */
//	public void dto2Form(BaseDTO dto){
//		BeanCopy.copyBean(dto,this);
//	}
	//////////////自动填充bean
	public void fillForm(HttpServletRequest request) {
		FormBeanFill.populate(request, this);

	}
	
}