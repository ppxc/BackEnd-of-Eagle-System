package com.example.demo.controller;

import com.example.demo.entity.OperationLog;
import com.example.demo.entity.Result;
import com.example.demo.service.OperationLogService;
import com.example.demo.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/log")
public class OperationLogController {

    private final OperationLogService operationLogService;
    private final JwtUtil jwtUtil;

    public OperationLogController(OperationLogService operationLogService, JwtUtil jwtUtil) {
        this.operationLogService = operationLogService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 写入操作日志
     * 前端只需传入 operation（操作描述）
     * 用户名、用户code、角色从当前登录用户自动获取
     */
    @PostMapping("/write")
    public Result<String> write(@RequestBody Map<String, String> body, HttpServletRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return Result.error("用户未登录");
            }

            String username = (String) auth.getPrincipal();
            String roles = auth.getAuthorities().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            String operation = body.get("operation");

            if (operation == null || operation.trim().isEmpty()) {
                return Result.error("操作描述不能为空");
            }

            String usercode = extractUsercodeFromRequest(request);

            OperationLog log = new OperationLog();
            log.setUsername(username);
            log.setUsercode(usercode);
            log.setRoles(roles);
            log.setOperation(operation);
            log.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            operationLogService.save(log);
            return Result.success("日志写入成功");
        } catch (Exception e) {
            return Result.error("日志写入失败: " + e.getMessage());
        }
    }

    /**
     * 查询操作日志（分页）
     * 可选参数: page, pageSize, username, usercode, startTime, endTime
     */
    @GetMapping("/query")
    public Result<Map<String, Object>> query(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> result = operationLogService.queryPage(params);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    private String extractUsercodeFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7);
            try {
                return jwtUtil.extractUsercode(jwt);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}