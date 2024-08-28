package top.mxzero.security.controller;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.mxzero.security.config.MinioConfig;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.entity.Member;
import top.mxzero.security.service.MemberService;

import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/27
 */
@Slf4j
@RestController
public class FileUploadController {
    private static final Set<String> SUPPORT_TYPES = Set.of("image/jpeg", "image/png", "image/gif");
    @Autowired
    private MinioClient client;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MinioConfig.MinioProps props;

    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 返回文件存储名称
     */
    @PostMapping("/api/upload/avatar")
    public RestData<?> fileUploadApi(@RequestParam MultipartFile file, Principal principal) {
        if (!supportFileType(file)) {
            return RestData.error("文件类型不支持");
        }
        String filename = "avatar/" + UUID.randomUUID() + "." + getExtendName(file);
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(props.getBucket()).contentType(file.getContentType()).object(filename).stream(file.getInputStream(), file.getSize(), 5 << 20).build();
            this.client.putObject(objectArgs);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestData.error("上传失败");
        }
        Member member = memberService.findByUsername(principal.getName());
        Member updateMember = new Member();
        updateMember.setId(member.getId());
        updateMember.setAvatar(filename);
        memberService.save(updateMember);
        return RestData.success(Map.of("filename", filename, "fileUrl", MinioConfig.OSS_RESOURCE_PREFIX + filename));
    }


    /**
     * 判断是否支持该文件上传
     *
     * @param file 文件
     * @return 支持返回true
     */
    private boolean supportFileType(MultipartFile file) {
        return SUPPORT_TYPES.contains(file.getContentType());
    }

    /**
     * 获取文件扩展名
     */
    private String getExtendName(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return null;
        }
        return file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
    }

}

