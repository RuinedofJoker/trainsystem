package org.hnxxxy.rg1b.controller.concern;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.domain.vo.ConcernUserVo;
import org.hnxxxy.rg1b.domain.vo.MessageWebSocketVo;
import org.hnxxxy.rg1b.service.concern.IPrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concern/message")
public class PrivateMessageController {

    @Autowired
    IPrivateMessageService privateMessageService;

    /**
     * 获取两个用户的历史私信
     * @param receiverUserId 私信接收者userId
     */
    @GetMapping("/history/{receiverUserId}")
    public ResultVo historyMessage(@PathVariable Long receiverUserId){
        Long userId = SecurityUtils.getUserId();
        List<MessageWebSocketVo> historyMessages = privateMessageService.selectHistoryMessage(userId, receiverUserId);

        return ResultVo.success(historyMessages);
    }

    /**
     * 获取当前用户的私信列表
     * @return
     */
    @GetMapping("/list")
    public ResultVo getMessageList() {
        List<ConcernUserVo> concernUserVos = privateMessageService.selectMessageList();
        return ResultVo.success(concernUserVos);
    }

    /**
     * 删除私信
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResultVo deleteSelfMessage(@PathVariable Long userId) {
        boolean deleted = privateMessageService.deleteSelfMessage(userId);
        return ResultVo.success("删除成功");
    }
}
