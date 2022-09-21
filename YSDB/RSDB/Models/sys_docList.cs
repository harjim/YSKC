using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 文件清单表，不同类型有不同的清单
    /// </summary>
    public class sys_docList:tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 清单类型，1001:研发成果管理, 1002: 附件清单
        /// </summary>
        public int listType { get; set; }
        /// <summary>
        /// 子分类：机构建设事项（研发技术中心）用 [0:组织管理制度，1:研发机构，2:成果转化奖励制度，3:人才绩效制度]
        /// </summary>
        public int? childType { get; set; }
        /// <summary>
        /// 文档名
        /// </summary>
        [Required,MaxLength(100)]
        public string docName { get; set; }
        /// <summary>
        /// 文档说明
        /// </summary>
        [MaxLength(200)]
        public string desciption { get; set; }
        /// <summary>
        /// 是否可选
        /// </summary>
        public bool optional { get; set; }
        /// <summary>
        /// 排序,公司自己添加的值都为999
        /// </summary>
        public int seq { get; set; }
        /// <summary>
        /// 默认为0,表示所有公司都上传的文档
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 分类
        /// </summary>
        [MaxLength(20)]
        public string classify { get; set; }
        /// <summary>
        /// 子分类
        /// </summary>
        [MaxLength(20)]
        public string subClassify { get; set; }
        /// <summary>
        /// 研发活动
        /// </summary>
        [MaxLength(50)]
        public string rdActivities { get; set; }
        [MaxLength(200)]
        public string samplePath { get; set; }

    }
}
