using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class projectmemberUIndex : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_project_member_mType_memberId_projectId",
                table: "project_member",
                columns: new[] { "mType", "memberId", "projectId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_member_mType_memberId_projectId",
                table: "project_member");
        }
    }
}
