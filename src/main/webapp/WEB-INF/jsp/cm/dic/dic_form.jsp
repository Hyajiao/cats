<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> --%>
           <input type="hidden" name="cdId" id="cdId" value="${dic.cdId }"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="cdType" class="col-sm-2 control-label">字典类型</label>
                                    <div class="col-sm-7">
                                        <%-- <form:select cssClass="form-control" id="cdType" name="cdType" path="selectId" items="${typeList}" itemLabel="key" itemValue="value" ></form:select> --%>
                                        <select class="form-control" name="cdType" id="cdType">	
						                  	<c:forEach items="${typeList}" var="s" varStatus="ss">
						                    	<option value ="${s.value }" <c:if test="${s.key == dic.cdType}">selected="selected"</c:if>>${s.key }</option>
											</c:forEach>	
				                  		</select>		
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="cdKey" class="col-sm-2 control-label">key</label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control" id="cdKey" name="cdKey" value="${dic.cdKey }"  placeholder="key">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="cdValue" class="col-sm-2 control-label">value</label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control" id="cdValue" name="cdValue" value="${dic.cdValue }" placeholder="value">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="cdDesc" class="col-sm-2 control-label">描述</label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control" id="cdMemo" name="cdMemo" value="${dic.cdMemo }" placeholder="描述">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->

                            <div class="box-footer">
                                <div class="col-xs-push-2 col-xs-2">
                                    <a class="btn btn-default" href="cm/dic/list" role="button">取消</a>
                                </div>
                                <div class="col-xs-push-4 col-xs-2">
                                    <button type="button" id="saveBtn" class="btn btn-info pull-right">保存</button>
                                </div>
                            </div>
                      

