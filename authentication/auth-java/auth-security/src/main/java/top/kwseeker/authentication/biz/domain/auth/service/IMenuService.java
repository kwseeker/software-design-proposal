package top.kwseeker.authentication.biz.domain.auth.service;

import java.util.List;

public interface IMenuService {

    List<Long> getMenuIdListByPermissionFromCache(String permission);
}
