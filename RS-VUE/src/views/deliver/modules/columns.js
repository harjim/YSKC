export const equipmentsColumns = [
  {
    field: 'ecode',
    title: '固定资产编码'
  },
  {
    field: 'ename',
    title: '固定资产名称'
  },
  {
    field: 'emodal',
    title: '规格型号'
  },
  {
    field: 'depreciation',
    title: '本期实提折旧额'
  },
  {
    field: 'rdTitle',
    title: '项目编号'
  },
  {
    field: 'workHours',
    title: '总工时'
  },
  {
    field: 'rdHour',
    title: '研发工时'
  },
  {
    field: 'rdDepreciation',
    title: '研发折旧'
  }
]

export const energiesColumns = [
  {
    field: 'unitPrice',
    title: '单价'
  },
  {
    field: 'quantity',
    title: '用量'
  },
  {
    field: 'sum',
    title: '金额'
  },
  {
    field: 'totalHour',
    title: '总工时'
  },
  {
    field: 'rdHour',
    title: '研发工时'
  },
  {
    field: 'rdQuantity',
    title: '研发用量'
  },
  {
    field: 'rdAmount',
    title: '研发金额'
  }
]

export const inspectionColumns = [
  {
    field: 'summary',
    title: '摘要'
  },
  {
    field: 'accDate',
    title: '记账日期'
  },
  {
    field: 'accNumber',
    title: '凭证号'
  },
  {
    field: 'type',
    title: '费用类型'
  },
  {
    field: 'deptName',
    title: '部门'
  },
  {
    field: 'expense',
    title: '费用'
  },
  {
    field: 'rdAmount',
    title: '研发费用'
  }
]

export const assistColumns = [
  {
    title: '年',
    children: [{ field: 'm', title: '月', minWidth: '50px' }, { field: 'd', title: '日', minWidth: '50px' }]
  },
  {
    title: '凭证',
    children: [{ title: '种类', minWidth: '50px' }, { field: 'voucherNo', title: '号数', minWidth: '50px' }]
  },
  {
    title: '摘要',
    field: 'typeName',
    minWidth: '100px'
  },
  {
    title: '借方金额',
    field: 'summary',
    minWidth: '100px'
  },
  {
    title: '贷方金额',
    minWidth: '100px'
  },
  {
    title: '借或贷',
    field: 'borrow',
    minWidth: '60px'
  },
  {
    title: '余额',
    field: 'amount',
    minWidth: '100px'
  },
  {
    title: '费用明细（借方）',
    children: [
      {
        title: '一、人员人工费用',
        children: [
          {
            title: '直接从事研发活动人员',
            children: [
              { title: '工资薪金', field: 'salary', minWidth: '100px' },
              { title: '五险一金', field: 'insurance', minWidth: '100px' }
            ]
          },
          {
            title: '外聘研发人员的劳务费用',
            minWidth: '100px'
          }
        ]
      },
      {
        title: '二、直接投入费用',
        children: [
          {
            title: '研发活动直接消耗',
            children: [
              { title: '材料', field: 'material', minWidth: '100px' },
              { title: '燃料', field: 'fuel', minWidth: '100px' },
              { title: '动力费用', field: 'stimulus', minWidth: '100px' }
            ]
          },
          {
            title: '用于中间试验和产品试制的模具、工艺装备开发及制造费',
            minWidth: '200px',
            field: 'trialProd'
          },
          {
            title: '用于不构成固定资产的样品、样机及一般测试手段购置费',
            minWidth: '200px',
            field: 'sampleMachine'
          },
          {
            title: '用于试制产品的检验费',
            minWidth: '200px',
            field: 'trialTest'
          },
          {
            title: '用于研发活动的仪器、设备的运行维护、调整、检验、维修等费用',
            minWidth: '200px',
            field: 'inspection'
          },
          {
            title: '通过经营租赁方式租入的用于研发活动的仪器、设备租赁费',
            minWidth: '200px'
          }
        ]
      },
      {
        title: '三、折旧费用',
        children: [
          {
            title: '用于研发活动的仪器的折旧费',
            minWidth: '100px',
            field: 'lab'
          },
          {
            title: '用于研发活动的设备的折旧费',
            minWidth: '100px',
            field: 'prod'
          }
        ]
      },
      {
        title: '四、无形资产摊销',
        children: [
          {
            title: '用于研发活动的软件的摊销费用',
            minWidth: '100px',
            field: 'softAmortization'
          },
          {
            title: '用于研发活动的专利权的摊销费用',
            minWidth: '100px',
            field: 'patentAmortization'
          },
          {
            title: '用于研发活动的非专利技术（包括许可证、专有技术、设计和计算方法等）的摊销费用',
            minWidth: '160px',
            field: 'otherAmortization'
          }
        ]
      },
      {
        title: '五、新产品设计费等',
        children: [
          {
            title: '新产品设计费',
            minWidth: '100px',
            field: 'design'
          },
          {
            title: '新工艺规程制定费',
            minWidth: '100px',
            field: 'techProcedure'
          },
          {
            title: '新药研制的临床试验费',
            minWidth: '100px',
            field: 'clinicalTrials'
          },
          {
            title: '勘探开发技术的现场试验费',
            minWidth: '100px',
            field: 'explore'
          }
        ]
      },
      {
        title: '六、其他相关费用',
        children: [
          {
            title: '技术图书资料费、资料翻译费、专家咨询费、高新科技研发保险费',
            minWidth: '160px',
            field: 'book'
          },
          {
            title: '研发成果的检索、分析、评议、论证、鉴定、评审、评估、验收费用',
            minWidth: '160px',
            field: 'rdProduction'
          },
          {
            title: '知识产权的申请费、注册费、代理费',
            minWidth: '100px',
            field: 'copyright'
          },
          {
            title: '职工福利费、补充养老保险费、补充医疗保险费',
            minWidth: '100px',
            field: 'benefits'
          },
          {
            title: '差旅费、会议费',
            minWidth: '110px',
            field: 'travel'
          },
          {
            title: '其他费用',
            minWidth: '110px',
            field: 'other'
          }
        ]
      }
    ]
  }
]
