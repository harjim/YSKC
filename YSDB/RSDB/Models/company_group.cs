using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class company_group
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        ///<summary>
        /// 集团id
        ///</summary>
        public int groupId { get; set; }
        ///<summary>
        /// 公司类型：1集团公司，2子公司
        ///</summary>
        public int companyType { get; set; }
        ///<summary>
        /// 全路径 形式:xxx/xxx/(以/结尾)
        ///</summary>
        public string fullPath { get; set; }
        ///<summary>
        /// 层级，标识当前节点的层级,从0开始
        ///</summary>
        public int level { get; set; }

        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        /// <summary>
        /// 管理端用户
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 管理端用户
        /// </summary>
        public int msLastUpdatorId { get; set; }
    }
}