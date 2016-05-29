package eduportal.model;

import java.util.List;

import eduportal.dao.UserDAO;
import eduportal.dao.entity.Order;
import eduportal.dao.entity.UserEntity;

public class AccessLogic {

	public static boolean canEditOrder(UserEntity admin, Order order) {
		if (admin.getAccessLevel() < AccessSettings.EDIT_ORDER) {
			return false;
		}
		if (admin.getPermission().getId() == AccessSettings.OWNERCORP().getId()) {
			return true;
		}
		UserEntity creator = order.createdByEntity();
		if (creator.corporationEntity() == admin.corporationEntity()) {
			return true;
		}
		return false;
	}

	public static boolean canCreateUser(UserEntity u) {
		if (u.getAccessLevel() < AccessSettings.CREATE_USER) {
			return false;
		}
		return true;
	}

	public static boolean canSeeTokens(String token) {
		if (AuthContainer.checkReq(token, AccessSettings.LIST_TOKENS)) {
			return true;
		}
		return false;
	}

	public static boolean canListAllUsers(String token) {
		UserEntity user = AuthContainer.getUser(token);
		if (user.corporationEntity().getId() == AccessSettings.OWNERCORP().getId()) {
			if (user.getAccessLevel() >= AccessSettings.LIST_USERS) {
				return true;
			}
		}
		return false;
	}

	public static List<UserEntity> listUsers(String phone, String name, String mail, String login, String token) {
		UserEntity user = AuthContainer.getUser(token);
		if (user.getAccessLevel() < AccessSettings.LIST_USERS) {
			return null;
		}
		return UserDAO.searchUsers(phone, name, mail, user.corporationEntity());
	}

	public static boolean canSeeAllProducts(String token) {
		UserEntity user = AuthContainer.getUser(token);
		System.out.println(AccessSettings.OWNERCORP());
		if (user.corporationEntity().getId() == AccessSettings.OWNERCORP().getId()) {
			if (user.getAccessLevel() >= AccessSettings.LIST_OFFLINE_PRODUCTS) {
				return true;
			}
		}
		return false;
	}

	public static boolean canSeeProducts(String token) {
		if (AuthContainer.getUser(token).getAccessLevel() >= AccessSettings.LIST_ACTUAL_PRODUCTS) {
			return true;
		}
		return false;
	}

	public static boolean canActivateProduct(String token) {
		UserEntity user = AuthContainer.getUser(token);
		if (user.corporationEntity().getId() == AccessSettings.OWNERCORP().getId()) {
			if (user.getAccessLevel() >= AccessSettings.DEACTIVATE_PRODUCTS) {
				return true;
			}
		}
		return false;
	}

	public static boolean canCreateCity(String token) {
		if (AuthContainer.getUser(token).getAccessLevel() >= AccessSettings.CREATE_CITY) {
			return true;
		}
		return false;
	}

	public static String canAddProduct(String token) {
		UserEntity user = AuthContainer.getUser(token);
		if (user.corporationEntity().getId() == AccessSettings.OWNERCORP().getId()) {
			if (user.getAccessLevel() >= AccessSettings.DEACTIVATE_PRODUCTS) {
				return "GOOD";
			}
			return "Access";
		}
		return "Corp";
	}

	public static boolean canSeeAllOrders(String token) {
		UserEntity user = AuthContainer.getUser(token);
		if (user.corporationEntity().getId() == AccessSettings.OWNERCORP().getId()) {
			if (user.getAccessLevel() >= AccessSettings.LIST_ALL_ORDERS) {
				return true;
			}
		}
		return false;
	}

	public static boolean canCancelOrder(String token, Order order) {
		if (!AuthContainer.checkReq(token, AccessSettings.CANCEL_ORDER)) {
			return false;
		}
		UserEntity admin = AuthContainer.getUser(token);
		if (admin.corporationEntity().getId() == AccessSettings.OWNERCORP().getId()) {
			return true;
		}
		UserEntity creator = order.createdByEntity();
		if (creator.corporationEntity() == admin.corporationEntity()) {
			return true;
		}
		return false;
	}
	
	public static boolean isCompanyAdmin (UserEntity user) {
		if (user.corporationEntity().getOwner().getId() == user.getId()) {
			return true;
		}
		if (AccessSettings.OWNERCORP().getOwner().getId() == user.getId()) {
			return true;
		}
		return false;
	}

	public static boolean canCreateOrder(UserEntity admin) {
		if (admin.getAccessLevel() >= AccessSettings.CREATE_ORDER) {
			return true;
		}
		return false;
	}
	
	public static boolean canAccessAdminPanel (UserEntity user) {
		if (user == null) {
			return false;
		}
		if (user.getAccessLevel() >= AccessSettings.ADMIN_LEVEL) {
			return true;
		}
		return false;
	}

	public static boolean canEditUser(UserEntity admin, UserEntity user) {
		if (admin.equals(user)) {
			return true;
		}
		if (admin.getAccessLevel() < AccessSettings.CREATE_USER) {
			return false;
		}
		if (admin.getCorporation() != AccessSettings.OWNERCORP().getId()) {
			if (admin.getCorporation() != user.getCorporation()) {
				return false;
			}
		}
		return true;
	} 
}
