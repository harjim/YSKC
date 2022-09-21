using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class projectmemberUIndexOrder : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_member_mType_memberId_projectId",
                table: "project_member");

            migrationBuilder.CreateIndex(
                name: "IX_project_member_projectId_mType_memberId",
                table: "project_member",
                columns: new[] { "projectId", "mType", "memberId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_project_member_projectId_mType_memberId",
                table: "project_member");

            migrationBuilder.CreateIndex(
                name: "IX_project_member_mType_memberId_projectId",
                table: "project_member",
                columns: new[] { "mType", "memberId", "projectId" },
                unique: true);
        }
    }
}
