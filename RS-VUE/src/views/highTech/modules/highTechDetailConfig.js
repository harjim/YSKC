/*
 * @Author: ldx
 * @Date: 2021-05-28 14:33:17
 * @LastEditTime: 2021-06-01 09:59:47
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\highTech\modules\highTechDetailConfig.js
 */
export default {
  /// 同类产品是否已有技术标准 [有，无]
  hasSameTechStandardOpt: [
    { value: false, label: '无' },
    { value: true, label: '有' }
  ],
  /// 技术标准 [0:国际标准,1:国家标准,2:行业标准,3:企业标准,4:其他]
  techStandardOpt: [
    { value: 0, label: '国际标准' },
    { value: 1, label: '国家标准' },
    { value: 2, label: '行业标准' },
    { value: 3, label: '企业标准' },
    { value: 4, label: '其他' }
  ],
  /// 新产品是否制定质量标准[有，无]
  hasQualityStandardOpt: [
    { value: false, label: '无' },
    { value: true, label: '有' }
  ],
  /// 新产品是否通过鉴定或第三方检测 [是，否]
  passTestOpt: [
    { value: false, label: '否' },
    { value: true, label: '是' }
  ],
  /// 新产品技术标准指标或鉴定指标是否达到国内外先进技术标准的水平
  /// [0:全部指标达国际标准水平, 1;部分指标达国际标准水平, 2:全部指标达国内标准先进水平,
  ///  3:部分指标达国内先进标准水平, 4:仅达到国内产品基本标准要求水平]
  techLevelOpt: [
    { value: 0, label: '全部指标达国际标准水平' },
    { value: 1, label: '部分指标达国际标准水平' },
    { value: 2, label: '全部指标达国内标准先进水平' },
    { value: 3, label: '部分指标达国内先进标准水平' },
    { value: 4, label: '仅达到国内产品基本标准要求水平' }
  ],
  /// 新产品是否国内首创新产品 [是，否]
  domesticFirstOpt: [
    { value: false, label: '否' },
    { value: true, label: '是' }
  ],
  /// 技术指标是否达到国际标准水平 [0:全部指标达国际标准先进水平,1:部分指标达国际标准先进水平,2全部指标与国际标准先进水平存在差距]
  internationalLevelOpt: [
    { value: 0, label: '全部指标达国际标准先进水平' },
    { value: 1, label: '部分指标达国际标准先进水平' },
    { value: 2, label: '全部指标与国际标准先进水平存在差距' }
  ],
  /// 项目来源 [0:国家计划,1:部门计划,2:省计划,3:市计划,4自行开发]
  projectSourceOpt: [
    { value: 0, label: '国家计划' },
    { value: 1, label: '部门计划' },
    { value: 2, label: '省计划' },
    { value: 3, label: '市计划' },
    { value: 4, label: '自行开发' }
  ],
  /// 技术来源 [0:国外技术,1:国内高校、科研院所技术,2:自有技术]
  techSourceOpt: [
    { value: 0, label: '国外技术' },
    { value: 1, label: '国内高校、科研院所技术' },
    { value: 2, label: '自有技术' }

  ],
  /// 产品开发形式 [0:合作开发,1:独立开发,2:消化引进吸收,3:其它]
  devTypeOpt: [
    { value: 0, label: '合作开发' },
    { value: 1, label: '独立开发' },
    { value: 2, label: '消化引进吸收' },
    { value: 3, label: '其它' }
  ],
  /// 自主知识产权含量 [0:核心技术,1:较高,2:一般,3:无]
  ownerPropertyOpt: [
    { value: 0, label: '核心技术' },
    { value: 1, label: '较高' },
    { value: 2, label: '一般' },
    { value: 3, label: '无' }
  ],
  /// 创新性 [0:首创,1:重大改进,2:较大改进,3:消化吸收]
  innovationOpt: [
    { value: 0, label: '首创' },
    { value: 1, label: '重大改进' },
    { value: 2, label: '较大改进' },
    { value: 3, label: '消化吸收' }
  ],
  /// 先进性 [0:国际领先,1:国际先进,2:国内领先,3:国内先进,4:省内先进]
  advancedOpt: [
    { value: 0, label: '国际领先' },
    { value: 1, label: '国际先进' },
    { value: 2, label: '国内领先' },
    { value: 3, label: '国内先进' },
    { value: 4, label: '省内先进' }
  ],
  /// 成熟度 [0:产品样机（样品）,1:小批量生产,2:批量（规模）生产]
  maturityOpt: [
    { value: 0, label: '产品样机（样品）' },
    { value: 1, label: '小批量生产' },
    { value: 2, label: '批量（规模）生产' }
  ],
  /// 产品获奖情况 [0:国家级,1:部、省级,2:市级,3:其它,4:无]
  awardOpt: [
    { value: 0, label: '国家级' },
    { value: 1, label: '部、省级' },
    { value: 2, label: '市级' },
    { value: 3, label: '其他' },
    { value: 4, label: '无' }
  ],
  detailMap: {
    award: {
      0: '国家级',
      1: '部、省级',
      2: '市级',
      3: '其他',
      4: '无'
    },
    maturity: {
      0: '产品样机（样品）',
      1: '小批量生产',
      2: '批量（规模）生产'
    },
    advanced: {
      0: '国际领先',
      1: '国际先进',
      2: '国内领先',
      3: '国内先进',
      4: '省内先进'
    },
    innovation: {
      0: '首创',
      1: '重大改进',
      2: '较大改进',
      3: '消化吸收'
    },
    ownerProperty: {
      0: '核心技术',
      1: '较高',
      2: '一般',
      3: '无'
    },
    devType: {
      0: '合作开发',
      1: '独立开发',
      2: '消化引进吸收',
      3: '其它'
    },
    techSource: {
      0: '国外技术',
      1: '国内高校、科研院所技术',
      2: '自有技术'
    },
    projectSource: {
      0: '国家计划',
      1: '部门计划',
      2: '省计划',
      3: '市计划',
      4: '自行开发'
    },
    internationalLevel: {
      0: '全部指标达国际标准先进水平',
      1: '部分指标达国际标准先进水平',
      2: '全部指标与国际标准先进水平存在差距'
    },
    techLevel: {
      0: '全部指标达国际标准水平',
      1: '部分指标达国际标准水平',
      2: '全部指标达国内标准先进水平',
      3: '部分指标达国内先进标准水平',
      4: '仅达到国内产品基本标准要求水平'
    },
    techStandard: {
      0: '国际标准',
      1: '国家标准',
      2: '行业标准',
      3: '企业标准',
      4: '其他'
    }

  }
}
