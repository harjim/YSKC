using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class tproject0921 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "t_project",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    pyear = table.Column<short>(nullable: false),
                    pname = table.Column<string>(maxLength: 200, nullable: false),
                    reportType = table.Column<string>(maxLength: 30, nullable: true),
                    aidType = table.Column<string>(maxLength: 30, nullable: true),
                    masterName = table.Column<string>(maxLength: 30, nullable: false),
                    masterTel = table.Column<string>(maxLength: 30, nullable: false),
                    linkName = table.Column<string>(maxLength: 30, nullable: false),
                    linkTel = table.Column<string>(maxLength: 30, nullable: false),
                    applyDate = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_project", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "t_project");
        }
    }
}
