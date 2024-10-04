package com.aeye.aeaimb.common.core.exception;


import com.aeye.aeaimb.common.core.util.R;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

public enum SystemMessage implements ExceptionCause<BusinessException> {
    OK(0,"成功"),
    ARGS_NULL(4000,"参数错误,必填参数 [{0}]"),
    TRANSLATE_ERROR(3000,"翻译报错"),
    ARGS_ERROR(4001,"参数错误,错误参数名[{0}],值[{1}],原因:{2}"),
    ARGS_ERROR2(4001,"参数错误，原因:{0}"),
    NOT_LOGIN(4002,"未登录或 session 失效"),
    PERMISSION_DENIED(4003,"没有权限"),
    DATA_PERMISSION_DENIED(4004,"无数据权限"),
    SIGN_ERROR(4005,"签名错误,你的签名串为 [{0}]"),
    CHANNEL_NOT_SUPPORT(4006,"非法访问"),
    ACCESS_DENIED(4007,"禁止访问 {0}"),

    SERVICE_CALL_FAIL(5000,"后台服务异常"),
    NETWORK_ERROR(5001,"网络连接错误或磁盘不可读"),
    DATA_TO_LARGE(5002,"数据过大"),
    REPEAT_DATA(5003,"数据重复 {0}"),
    NOT_SUPPORT_OPERATOR(5004,"不支持的操作"),
    NOT_SUPPORT_MIME(5005,"不支持的 MIME类型,当前类型为:{0}"),
    POOL_OBJECT_NOT_ENOUGH(5006,"对象池[{0}]对象不足"),
    CALL_MODUL_FAIL(5007,"{0} 模块调用错误"),

    FILE_INCOMPLETE(5901,"文件不完整，上传失败"),
    FILE_CORRUPTED(5902,"文件损坏，上传失败"),
    FILE_TOO_LARGE(5903,"文件过大，当前文件大小:{0}，超过最大上传大小:{1}"),
    FILE_TYPE_NOT_ALLOW(5904,"文件类型不被支持，当前文件类型:{0},支持的文件类型为:{1}"),


	ORG_NOT_EXIST(6000,"组织结构不存在"),
	ORG_SAVE_FAILED(6001,"组织密钥保存失败"),
	ORG_DEL_FAILED(6002,"机构不能删除，存在院区或子机构"),
	ORG_AREA_DEL_FAILED(6003,"机构不能删除，存在科室"),
	DEPT_DEL_FAILED(6004,"科室不能删除，其存在人员数据"),
	DOCTOR_ADD_FAILED(6005,"新增医护人员，需要传选择的机构id"),
	ORG_AREA_DEL1_FAILED(6006,"该行政区域下有组织机构，不能删除"),
	ORG_CODE_VALIDATE(6007,"组织机构代码已经存在数据库，请重新设置"),
	ORG_NAME_VALIDATE(6008,"组织机构名称已经存在数据库，请重新设置"),
	ORG_NAME_SP_VALIDATE(6009,"组织机构简称已经存在数据库，请重新设置"),
	APP_NAME_VALIDATE(6010,"应用名称已经存在数据库，请重新设置"),
	APP_NAME_SP_VALIDATE(6011,"应用编码已经存在数据库，请重新设置"),
	DEPT_NAME_VALIDATE(6012,"科室名称已经存在数据库，请重新设置"),
	DEPT_CODE_VALIDATE(6013,"科室编码已经存在数据库，请重新设置"),
	ROLE_CODE_VALIDATE(6014,"角色标识已经存在数据库，请重新设置"),
	ROLE_NAME_VALIDATE(6015,"角色名称已经存在数据库，请重新设置"),
	DRUG_CODE_VALIDATE(6016,"该院方商品代码已映射标准药品字典，请勿重复映射"),
	DOCTOR_CODE_VALIDATE(6017,"人员编码在所选机构已经存在，请重新设置"),
	SERVICE_CODE_VALIDATE(6018,"该院方项目编码已映射标准医疗服务项目字典，请勿重复映射"),
	DEPT_DICT_CODE_VALIDATE(6019,"科室字典编码已经存在数据库，请重新设置"),
	DEPT_DICT_NAME_VALIDATE(6020,"科室字典名称已经存在数据库，请重新设置"),
	DEPT_DICT_NO_VALIDATE(6021,"更新操作传的科室编码在数据库找不到对应数据"),
	DEPT_DICT_DELETE_VALIDATE(6022,"该科室包含下级科室或映射医院科室，不能删除"),
	DRUG_DICT_CODE_VALIDATE(6023,"药品代码已经存在，请重新设置"),
	DRUG_DICT_NO_VALIDATE(6022,"更新操作传的药品编码在数据库找不到对应数据"),
	DRUG_DICT_DELETE_VALIDATE(6023,"该药品已映射医院药品字典或映射知识库，不能删除"),
	SYS_USER_NAME_VALIDATE(6024,"所选人员的机构下此用户名已经存在，请重新设置"),
	ROLE_USER_COUNT_VALIDATE(6025,"该角色用户数＞0，不能删除"),
	OP_DICT_CODE_VALIDATE(6026,"手术操作编码已经存在数据库，请重新设置"),
	OP_DICT_NAME_VALIDATE(6027,"手术操作名称已经存在数据库，请重新设置"),
	OP_DICT_CODE_NO_VALIDATE(6028,"更新操作传的手术编码在数据库找不到对应数据"),
	DISEASE_DICT_CODE_VALIDATE(6029,"疾病编码已经存在数据库，请重新设置"),
	DISEASE_DICT_NAME_VALIDATE(6030,"疾病名称已经存在数据库，请重新设置"),
	DISEASE_DICT_CODE_NO_VALIDATE(6031,"更新操作传的疾病编码在数据库找不到对应数据"),
	DISEASE_DEL_NO_VALIDATE(6032,"该疾病已映射医院疾病字典或映射知识库，不能删除"),
	OP_DEL_NO_VALIDATE(6033,"该手术已映射医院手术字典或映射知识库，不能删除"),

	SYMPTOM_DICT_CODE_VALIDATE(6034,"症状编码已经存在数据库，请重新设置"),
	SYMPTOM_DICT_NAME_VALIDATE(6035,"症状名称已经存在数据库，请重新设置"),
	SYMPTOM_DICT_CODE_NO_VALIDATE(6036,"更新操作传的症状编码在数据库找不到对应数据"),
	SYMPTOM_DICT_DEL_NO_VALIDATE(6037,"该症状已匹配知识库数据，不能删除"),

	SERVICE_DICT_CODE_VALIDATE(6038,"服务项目编码已经存在数据库，请重新设置"),
	SERVICE_DICT_NAME_VALIDATE(6039,"服务项目名称已经存在数据库，请重新设置"),
	SERVICE_DICT_CODE_NO_VALIDATE(6040,"更新操作传的服务项目编码在数据库找不到对应数据"),
	SERVICE_DICT_DEL_VALIDATE(6041,"该项目已映射医院医疗服务项目字典或映射知识库，不能删除"),

	AREA_DICT_CODE_VALIDATE(6042,"区域字典编码已经存在数据库，请重新设置"),
	AREA_DICT_NAME_VALIDATE(6043,"区域字典名称已经存在数据库，请重新设置"),
	AREA_DICT_NO_VALIDATE(6044,"更新操作传的区域编码在数据库找不到对应数据"),
	SUPPER_ADMIN_VALIDATE(6045,"超管用户账号不允许添加，请重新设置"),
	PHONE_ONE_VALIDATE(6046,"手机号已经存在，请重新设置"),

	ORG_AREA_NAME_VALIDATE(6047,"院区名称已经存在数据库，请重新设置"),
	ORG_AREA_CODE_VALIDATE(6048,"院区编码已经存在数据库，请重新设置"),
	QR_CODE_VALIDATE(6049,"设备编码不存在"),
	APP_MENU_EXIST_VALIDATE(6050,"该应用下有菜单数据，不能删除"),
	SYSUSER_NOT_REFID_VALIDATE(6051,"机构人员应不能修改"),
	OPERATE_MAPPING_CODE_VALIDATE(6052,"该院方手术已映射当前版本标准手术字典，请勿重复映射"),
	DISEASE_MAPPING_CODE_VALIDATE(6053,"该院方疾病已映射当前版本标准疾病字典，请勿重复映射"),
	DICT_CODE_VALIDATE(6054,"字典类型已经存在数据库，请重新设置"),
	DICT_TYPE_VALIDATE(6055,"字典编码已经存在数据库，请重新设置"),
	DICT_TYPE_NO_VALIDATE(6056,"疾病字典编码对应的数据不存在，请联系管理员"),
	APP_KEY_EXISTED_VALIDATE(6057,"AppKey已经存在，请重新设置"),


	/**
	 * 8开头作为图谱业务逻辑的错误，这里要对编码进行分类数据，要具有明确的错误信息
	 */
	DIAG_NOT_EXIST_HUGE(8001, "疾病在图谱中不存在：{0}"),

	/**
	 * 9开头作为系统内部运行的错误，这里要对编码进行分类数据，要具有明确的错误信息
	 */

	SYS_INNER_PARAM_EX(9000, "参数类型解析异常"),
	SYS_INNER_DATA_CONVERT_ERROR(9001, "数据转换异常"),


	/**
	 * 11开头作为远程协助的错误
	 */
	PATIENT_CONCERN_VALIDATE(110001,"患者已经关注，无须继续关注"),
	APPLY_NO_EXIST_VALIDATE(110002,"申请单不存在"),
	APPLY_REPORT_NO_EXIST(110003,"远程协助报告还没有生成，不允许结束"),
	DATA_ERROR(110004,"数据异常:{0}"),


	/**
	 * 12开头
	 */
	TCMS_SYMP_NO_EXIST(120002,"症状编码:{0}未找到，请联系管理员"),

	/**
	 * 13开头 知识中台-问诊路径模块
	 */
	PATH_NAME_CANNOT_BE_DUPLICATED(130001,"路径名称不能重复"),

	PURPOSE_NAME_CANNOT_BE_DUPLICATED(130002,"目标名称不能重复"),


	PARENT_PURPOSE_ALREADY_HAS_CHILD_PURPOSE(130003,"主目标下已经有子目标，不可以修改成子目标"),


	KEYWORD_FRONT_MUST(130004,"keyword前端必传"),

	ID_FRONT_MUST(130005,"id前端必传"),

	QUESTION_CANNOT_BE_DUPLICATED(130006,"问题不能重复"),

	QUESTION_OPTION_CANNOT_BE_DUPLICATED(130007,"同一个问题选项不能重复"),
	;
    R responseDto = new R();
    public MessageFormat messageFormat;

    private SystemMessage(int returnCode,String message){
        responseDto.setCode(returnCode+"");
        responseDto.setMsg(message);
		try {
			messageFormat = new MessageFormat(message);
		}catch (Exception e){
			System.out.println("消息创建 messageFormat 异常, 检查代码并修复 "+ returnCode + " ==> "+  message);
		}
    }

    public BusinessException exception(Object...args) {
        return BusinessException.create(this,args);
    }

    public MessageFormat getMessageFormat() {
        return messageFormat;
    }

    public R result() {
        return responseDto;
    }

    /**
     * 自定义消息的结果返回
     * @param args
     * @return
     */
    public R result(Object ... args){
        String message = responseDto.getMsg();
        responseDto.setMsg(MessageFormat.format(message,args));
        return responseDto;
    }

    public String getReturnCode(){
        return responseDto.getCode();
    }
}
