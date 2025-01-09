package project.communityservice.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.communityservice.domain.service.impl.CommunityServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityServiceImpl communityServiceImpl;
}
