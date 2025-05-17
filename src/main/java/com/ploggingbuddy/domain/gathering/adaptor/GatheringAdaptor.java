package com.ploggingbuddy.domain.gathering.adaptor;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.global.annotation.adaptor.Adaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GatheringAdaptor {

    private final GatheringAdaptor gatheringAdaptor;

}
