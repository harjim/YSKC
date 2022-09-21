using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterFlowInstanceCurNodeIdsColCanBeNull : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "parentId",
                table: "flowInstance_curNode",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<int>(
                name: "nodeStatus",
                table: "flowInstance",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<int>(
                name: "curNodeId",
                table: "flowInstance",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<int>(
                name: "auditLevel",
                table: "flowInstance",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "parentId",
                table: "flowInstance_curNode");

            migrationBuilder.AlterColumn<int>(
                name: "nodeStatus",
                table: "flowInstance",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "curNodeId",
                table: "flowInstance",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "auditLevel",
                table: "flowInstance",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
