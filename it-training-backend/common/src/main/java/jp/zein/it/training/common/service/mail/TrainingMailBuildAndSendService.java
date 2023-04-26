package jp.zein.it.training.common.service.mail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;

/**
 * メール作成および送信サービス
 */
public interface TrainingMailBuildAndSendService {
	/**
	 * メールの作成および送信（CC,BCC省略)
	 *
	 * @param fromAddress
	 * @param toAddressList
	 * @param subjectTemplateBasePath サブジェクト・テンプレートの基底パス
	 * @param bodyTemplateBasePath    本文テンプレートの基底パス
	 * @param languageCode            サブジェクトおよび本文の言語コード
	 * @param templateParameterMap    パラメータ
	 * @throws IOException
	 */
	public void buildAndSend(String fromAddress, List<String> toAddressList, String subjectTemplateBasePath,
			String bodyTemplateBasePath, String languageCode, Map<String, Object> templateParameterMap)
			throws IOException;

	/**
	 * メールの作成および送信
	 *
	 * @param fromAddress
	 * @param toAddressList
	 * @param ccAddressList
	 * @param bccAddressList
	 * @param subjectTemplateBasePath サブジェクト・テンプレートの基底パス
	 * @param bodyTemplateBasePath    本文テンプレートの基底パス
	 * @param languageCode            サブジェクトおよび本文の言語コード
	 * @param templateParameterMap    パラメータ
	 * @throws IOException
	 */
	public void buildAndSend(String fromAddress, List<String> toAddressList, List<String> ccAddressList,
			List<String> bccAddressList, String subjectTemplateBasePath, String bodyTemplateBasePath,
			String languageCode, Map<String, Object> templateParameterMap) throws IOException;

	
	/**
	 * メールの作成および送信（添付ファイルあり、CC,BCC省略)
	 * 
	 * @param fromAddress
	 * @param toAddressList
	 * @param subjectTemplateBasePath 	サブジェクト・テンプレートの基底パス
	 * @param bodyTemplateBasePath		本文テンプレートの基底パス
	 * @param languageCode				サブジェクトおよび本文の言語コード
	 * @param templateParameterMap		パラメータ
	 * @param fileResources				添付ファイルリソース
	 * @throws IOException
	 */
	public void buildAndSendWithFiles(String fromAddress, List<String> toAddressList, 
			String subjectTemplateBasePath, String bodyTemplateBasePath,
			String languageCode, Map<String, Object> templateParameterMap, Map<String, Resource> fileResources) throws IOException;
	
	/**
	 * 
	 * @param fromAddress
	 * @param toAddressList
	 * @param ccAddressList
	 * @param bccAddressList
	 * @param subjectTemplateBasePath	サブジェクト・テンプレートの基底パス
	 * @param bodyTemplateBasePath		本文テンプレートの基底パス
	 * @param languageCode				サブジェクトおよび本文の言語コード
	 * @param templateParameterMap		パラメータ
	 * @param fileResources				添付ファイルリソース
	 * @throws IOException
	 */
	public void buildAndSendWithFiles(String fromAddress, List<String> toAddressList, List<String> ccAddressList,
			List<String> bccAddressList, String subjectTemplateBasePath, String bodyTemplateBasePath,
			String languageCode, Map<String, Object> templateParameterMap, Map<String, Resource> fileResources) throws IOException;
	
	

}
