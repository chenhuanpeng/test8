package com.aeye.aeaimb.common.security.component;

import cn.hutool.extra.spring.SpringUtil;
import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.security.service.CdssUser;
import com.aeye.aeaimb.common.security.service.CdssUserDetailsService;
import com.aeye.aeaimb.common.security.service.MkpbUser;
import com.aeye.aeaimb.common.security.service.MkpbUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.util.*;

/**
 * @author lengleng
 * @date 2022/5/28
 */
@Slf4j
@RequiredArgsConstructor
public class CdssCustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

	private final OAuth2AuthorizationService authorizationService;

	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
		if (Objects.isNull(oldAuthorization)) {
			throw new InvalidBearerTokenException(token);
		}

		// 客户端模式默认返回
		if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(oldAuthorization.getAuthorizationGrantType())) {
			HashMap<String, Object> attr = new HashMap<>();
			attr.putAll(oldAuthorization.getAttributes());
			attr.put("1","1");
			return new DefaultOAuth2AuthenticatedPrincipal(oldAuthorization.getPrincipalName(),
					attr, AuthorityUtils.NO_AUTHORITIES);
		}

//		Map<String, CdssUserDetailsService> userDetailsServiceMap = SpringUtil
//			.getBeansOfType(CdssUserDetailsService.class);
//
//		Optional<CdssUserDetailsService> optional = userDetailsServiceMap.values()
//			.stream()
//			.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
//					oldAuthorization.getAuthorizationGrantType().getValue()))
//			.max(Comparator.comparingInt(Ordered::getOrder));

		UserDetails userDetails = null;
		try {
			Object principal = Objects.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
			Object tokenPrincipal = usernamePasswordAuthenticationToken.getPrincipal();
			if (tokenPrincipal instanceof MkpbUser){

				Map<String, MkpbUserDetailsService> userDetailsServiceMap = SpringUtil
						.getBeansOfType(MkpbUserDetailsService.class);

				Optional<MkpbUserDetailsService> optional = userDetailsServiceMap.values()
						.stream()
						.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
								oldAuthorization.getAuthorizationGrantType().getValue()))
						.max(Comparator.comparingInt(Ordered::getOrder));

				userDetails = optional.get().loadUserByUser((MkpbUser) tokenPrincipal);
			}
			if (tokenPrincipal instanceof CdssUser){
				Map<String, CdssUserDetailsService> userDetailsServiceMap = SpringUtil
						.getBeansOfType(CdssUserDetailsService.class);

				Optional<CdssUserDetailsService> optional = userDetailsServiceMap.values()
						.stream()
						.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
								oldAuthorization.getAuthorizationGrantType().getValue()))
						.max(Comparator.comparingInt(Ordered::getOrder));
				userDetails = optional.get().loadUserByUser((CdssUser) tokenPrincipal);
			}
		}
		catch (UsernameNotFoundException notFoundException) {
			log.warn("用户不不存在 {}", notFoundException.getLocalizedMessage());
			throw notFoundException;
		}
		catch (Exception ex) {
			log.error("资源服务器 introspect Token error {}", ex.getLocalizedMessage());
		}
		// 注入扩展属性,方便上下文获取客户端ID
		if (userDetails instanceof MkpbUser){
			MkpbUser user = (MkpbUser) userDetails;
			Objects.requireNonNull(user)
					.getAttributes()
					.put(SecurityConstants.CLIENT_ID, oldAuthorization.getRegisteredClientId());
			return user;
		}
		if (userDetails instanceof CdssUser){
			CdssUser user = (CdssUser) userDetails;
			Objects.requireNonNull(user)
					.getAttributes()
					.put(SecurityConstants.CLIENT_ID, oldAuthorization.getRegisteredClientId());
			return user;
		}
		return null;
	}

}
