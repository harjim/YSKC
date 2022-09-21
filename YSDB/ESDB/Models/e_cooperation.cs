using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class e_cooperation
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        ///意向/感兴趣对象(自己不能对自己有意向)（意向id,存储对应类型的数据id）
        ///</summary>
        public int intentionId { get; set; }
        ///<summary>
        ///单位名称
        ///</summary>
        [MaxLength(100), Required]
        public string unitName { get; set; }
        ///<summary>
        /// 姓名
        ///</summary>
        [MaxLength(20), Required]
        public string fullname { get; set; }
        ///<summary>
        ///联系方式-->手机号码
        ///</summary>
        [MaxLength(50), Required]
        public string linkInfo { get; set; }
        ///<summary>
        ///邮箱
        ///</summary>
        [MaxLength(50)]
        public string email { get; set; }
        ///<summary>
        ///备注
        ///</summary>
        [MaxLength(500)]
        public string remark { get; set; }

        ///<summary>
        /// 0:未沟通,1:达成合作,2:服务中
        ///</summary>
        public int status { get; set; }

        ///<summary>
        /// 意向信息(意向内容：达成合作填写)
        ///</summary>
        [MaxLength(500)]
        public string information { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        ///<summary>
        /// 若当前人是登录状态，则存储该userId
        ///</summary>
        public int? creatorId { get; set; }
        ///<summary>
        /// ms 操作人
        ///</summary>
        public int? msLastUpdatorId { get; set; }
        
        ///<summary>
        /// 意向/感兴趣类型：0：专家、1：科技成果、2：技术需求
        public int type { get; set; }

    }
}