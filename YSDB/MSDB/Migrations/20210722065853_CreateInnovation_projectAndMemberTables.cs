using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class CreateInnovation_projectAndMemberTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "innovation_member",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    mType = table.Column<int>(nullable: false),
                    memberId = table.Column<int>(nullable: false),
                    isMaster = table.Column<bool>(nullable: false),
                    innovationId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_innovation_member", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "innovation_project",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    deptId = table.Column<int>(nullable: false),
                    types = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_innovation_project", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_innovation_member_innovationId_mType_memberId",
                table: "innovation_member",
                columns: new[] { "innovationId", "mType", "memberId" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_innovation_project_customerId_year",
                table: "innovation_project",
                columns: new[] { "customerId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "innovation_member");

            migrationBuilder.DropTable(
                name: "innovation_project");
        }
    }
}
